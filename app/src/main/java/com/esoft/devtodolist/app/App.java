package com.esoft.devtodolist.app;

import android.app.Application;

import androidx.room.Room;

import com.esoft.devtodolist.base.AppDataBase;
import com.esoft.devtodolist.model.RepositoryDao;

public class App extends Application {

    private AppDataBase appDataBase;
    private RepositoryDao repositoryDao;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        appDataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "appDbName")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


        repositoryDao = appDataBase.repositoryDao();

    }

    public AppDataBase getAppDataBase() {
        return appDataBase;
    }

    public void setAppDataBase(AppDataBase appDataBase) {
        this.appDataBase = appDataBase;
    }

    public RepositoryDao getRepositoryDao() {
        return repositoryDao;
    }

    public void setRepositoryDao(RepositoryDao noteDao) {
        this.repositoryDao = noteDao;
    }
}
