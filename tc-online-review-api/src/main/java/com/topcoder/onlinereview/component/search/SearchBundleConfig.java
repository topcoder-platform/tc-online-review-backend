package com.topcoder.onlinereview.component.search;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "search.bundles")
public class SearchBundleConfig {

  private List<ConfigData> data;

  public static class ConfigData {
    private String name;
    private String context;
    private Map<String, String> alias;
    private List<String> fields;

    public void setName(String name) {
      this.name = name;
    }

    public void setContext(String context) {
      this.context = context;
    }

    public void setAlias(Map<String, String> alias) {
      this.alias = alias;
    }

    public void setFields(List<String> fields) {
      this.fields = fields;
    }

    public String getName() {
      return name;
    }

    public String getContext() {
      return context;
    }

    public Map<String, String> getAlias() {
      return alias;
    }

    public List<String> getFields() {
      return fields;
    }
  }
}
