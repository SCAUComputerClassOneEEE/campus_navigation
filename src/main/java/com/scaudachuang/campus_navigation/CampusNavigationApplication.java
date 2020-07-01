package com.scaudachuang.campus_navigation;

import com.scaudachuang.campus_navigation.fx.AbstractFxApplication;
import com.scaudachuang.campus_navigation.fx.FxmlView;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CampusNavigationApplication extends AbstractFxApplication {

    public static void main(String[] args) {
        run(CampusNavigationApplication.class,
                Arrays.asList(FxmlView.MAIN,FxmlView.LOGIN),
                FxmlView.LOGIN, args);    }
}
