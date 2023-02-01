package com.sjx.poi.core;


import com.sjx.poi.listener.TransferListener;

/**
 * author： hanwang
 * time: 2020/12/31  17:49
 */
class ExportParameter {
    private ParameterInfo.PathParameterInfo path;
    private ParameterInfo.DataParameterInfo data;
    private ParameterInfo.ListenerParameterInfo listener;
    private ParameterInfo.LazyParameterInfo lazy;
    private ParameterInfo.TagParameterInfo tag;
    private ParameterInfo.SheetParameterInfo sheet;

    private ExportParameter(Builder builder){
        path=builder.path;
        data=builder.data;
        listener=builder.listener;
        lazy=builder.lazy;
        tag=builder.tag;
        sheet=builder.sheet;
    }

    public ParameterInfo.SheetParameterInfo getSheet() {
        return sheet;
    }

    public ParameterInfo.PathParameterInfo getPath() {
        return path;
    }


    public ParameterInfo.DataParameterInfo getData() {
        return data;
    }

    public ParameterInfo.LazyParameterInfo getLazy() {
        return lazy;
    }

    public ParameterInfo.ListenerParameterInfo getListener() {
        return listener;
    }

    public ParameterInfo.TagParameterInfo getTag() {
        return tag;
    }

    public   static class Builder{
        private ParameterInfo.PathParameterInfo path;
        private ParameterInfo.DataParameterInfo data;
        private ParameterInfo.ListenerParameterInfo listener;
        private ParameterInfo.LazyParameterInfo lazy;
        private ParameterInfo.TagParameterInfo tag;
        private ParameterInfo.SheetParameterInfo sheet;

        public ExportParameter.Builder setTransferSheet(ParameterInfo.SheetParameterInfo sheet) {
            this.sheet = sheet;
            return this;
        }

        public ExportParameter.Builder setTransferPath(ParameterInfo.PathParameterInfo path) {
            this.path = path;
            return this;
        }

        public ExportParameter.Builder setTransferData(ParameterInfo.DataParameterInfo data) {
            this.data = data;
            return this;
        }

        public ExportParameter.Builder setTransferListener(ParameterInfo.ListenerParameterInfo listener) {
            this.listener = listener;
            return this;
        }
        public ExportParameter.Builder setTransferLazy(ParameterInfo.LazyParameterInfo lazy) {
            this.lazy = lazy;
            return this;
        }

        public ExportParameter.Builder setTransferTag(ParameterInfo.TagParameterInfo tag) {
            this.tag = tag;
            return this;
        }

        public ExportParameter build(){
            if (path==null){

            }
            if (data==null){

            }
            return new ExportParameter(this);
        }


    }

}