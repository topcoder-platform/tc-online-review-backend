package com.topcoder.onlinereview.component.search;

import lombok.Data;

import java.util.Map;

@Data
public class SearchBundleConfig {
    private String name;
    private String context;
    private Map<String, String> alias;
    private Map<String, String> fields;
}
