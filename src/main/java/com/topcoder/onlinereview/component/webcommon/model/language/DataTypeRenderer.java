package com.topcoder.onlinereview.component.webcommon.model.language;

public class DataTypeRenderer implements ElementRenderer {
    private DataType dataType;

    public DataTypeRenderer() {
        this.dataType = null;
    }

    public DataTypeRenderer(DataType dataType) {
        this.dataType = dataType;
    }

    public void setElement(Element element) throws Exception {
        this.dataType = (DataType)element;
    }

    public String toHTML(Language language) {
        String desc = this.dataType.getDescriptor(language);
        return desc == null ? "null" : BaseRenderer.encodeHTML(desc);
    }

    public String toPlainText(Language language) {
        String desc = this.dataType.getDescriptor(language);
        return desc == null ? "null" : desc;
    }
}

