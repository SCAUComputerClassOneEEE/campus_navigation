package com.scaudachuang.campus_navigation.fx;

import java.util.ResourceBundle;

public enum FxmlView {
    MAIN {
        @Override
		public String title() {
            return getStringFromResourceBundle("app.title");
        }

        @Override
		public String fxml() {
            return "/fxml/ManagementView.fxml";
        }

    },

    LOGIN{
        @Override
        public String title() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String fxml() {
            return "/fxml/login.fxml";
        }
    }
    ;


    
    public abstract String title();
    public abstract String fxml();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
