package com.example.project;

public class TaskInfo {
    public static class Process {
        private String prc_ProcessName;
        private String prc_PID;
        private String prc_SessionName;
        private String prc_Session;
        private String prc_MemUsage;

        public Process(String prc_ProcessName, String prc_PID, String prc_SessionName, String prc_Session, String prc_MemUsage) {
            this.prc_ProcessName = prc_ProcessName;
            this.prc_PID = prc_PID;
            this.prc_SessionName = prc_SessionName;
            this.prc_Session = prc_Session;
            this.prc_MemUsage = prc_MemUsage;
        }

        public String getPrc_ProcessName() {
            return prc_ProcessName;
        }
        public Process() {}

        public String getPrc_PID() {
            return prc_PID;
        }

        public String getPrc_SessionName() {
            return prc_SessionName;
        }

        public String getPrc_Session() {
            return prc_Session;
        }

        public String getPrc_MemUsage() {
            return prc_MemUsage;
        }

        public void setPrc_ProcessName(String prc_ProcessName) {
            this.prc_ProcessName = prc_ProcessName;
        }

        public void setPrc_PID(String prc_PID) {
            this.prc_PID = prc_PID;
        }

        public void setPrc_SessionName(String prc_SessionName) {
            this.prc_SessionName = prc_SessionName;
        }

        public void setPrc_Session(String prc_Session) {
            this.prc_Session = prc_Session;
        }

        public void setPrc_MemUsage(String prc_MemUsage) {
            this.prc_MemUsage = prc_MemUsage;
        }
    }
    public static class Application {
        private String app_Name;

        public Application(String app_Name) {
            this.app_Name = app_Name;
        }

        public String getApp_Name() {
            return app_Name;
        }
        public Application(){}
        public void setApp_Name(String app_Name) {
            this.app_Name = app_Name;
        }
    }
}
