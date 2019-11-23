package ar.edu.itba.barsahome.api;

import androidx.annotation.NonNull;

public class Routine {
    private String id;
    private String name;
    private Actions[] actions;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActions(Actions[] actions) {
        this.actions = actions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Actions[] getActions() {
        return actions;
    }

    public class Actions{
        private Device device;
        private String actionName;
        private Object params;

        public Device getDevice() {
            return device;
        }

        public String getActionName() {
            return actionName;
        }

        public Object getParams() {
            return params;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public void setParams(Object params) {
            this.params = params;
        }

        @NonNull
        @Override
        public String toString() {
            return this.actionName + this.params == null ? "null" : this.params.toString();
        }
    }
}
