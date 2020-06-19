package com.scaudachuang.campus_navigation.fx;

import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.fx.view.LoginView;
import com.scaudachuang.campus_navigation.fx.view.ManagementView;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.sun.javafx.application.LauncherImpl.launchApplication;

@SuppressWarnings("restriction")
public abstract class AbstractFxApplication extends Application {
	@Resource
	private AdminService adminService;
	// pre-load views
	protected static List<FxmlView> preloadViews;
	protected static FxmlView initView;

	// spring context
	protected static ConfigurableApplicationContext applicationContext;

	// pre-load status
	private float progress = 0;

	public static void run(final Class<? extends Application> appClass, final List<FxmlView> _preloadViews,
						   final FxmlView _initView, final String[] args) {
		preloadViews = _preloadViews;
		initView = _initView;

		CompletableFuture.supplyAsync(() -> applicationContext = SpringApplication.run(appClass, args))
				.whenComplete((ctx, throwable) -> {
					if (throwable != null) {
						//LOGGER.error("Failed to load spring application context: ", throwable);
					} else {
						launchApplication(appClass, FxAppPreloader.class, args);
					}
				});
	}

	@Override
	public synchronized void init() {
		try {
			for (FxmlView view : preloadViews) {
				// load view
				FXMLLoader.load(getClass().getResource(view.fxml()));

				// update loader status
				notifyLoader();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized void notifyLoader() {
		progress += 100f / preloadViews.size();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
		LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
	}

	@Override
	public void start(Stage primary) {
/*		GNDecorator decorator = new GNDecorator();
		stageManager = applicationContext.getBean(StageManager.class, primary, decorator);
		stageManager.switchScene(initView);
		stageManager.showDecorator();*/
		LoginView loginView = new LoginView();
		Scene scene = new Scene(loginView.root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		loginView.getLogin().setOnAction(event -> {
			Admin admin = adminService.findAdminByAdminName(loginView.getUserName().getText());
			String password = loginView.getPassword().getText();
			if (admin.getPassword().equals(password)){
				System.out.println("登录成功");
				ManagementView managementView = new ManagementView();
				scene.setRoot(managementView.getBorderPane());
			}
			else {
				System.out.println("账号或密码错误");
			}
		});

	}

	@Override
	public void stop() throws Exception {
		super.stop();
		if (applicationContext != null) {
			applicationContext.close();
		}
	}

}
