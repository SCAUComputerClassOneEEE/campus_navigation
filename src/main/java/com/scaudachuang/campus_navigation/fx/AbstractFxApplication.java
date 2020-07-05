package com.scaudachuang.campus_navigation.fx;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.sun.javafx.application.LauncherImpl.launchApplication;

@SuppressWarnings("restriction")
public abstract class AbstractFxApplication extends Application {

	// stage manager
	protected StageManager stageManager;

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
		stageManager = applicationContext.getBean(StageManager.class, primary);
		stageManager.switchScene(initView);
		stageManager.show();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		if (applicationContext != null) {
			applicationContext.close();
		}
	}


}
