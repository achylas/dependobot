package com.DependobotScanner.dependobot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DependabotAlert {
    private String state;
    private Dependency dependency;
    private SecurityVulnerability securityVulnerability;

    @Data
    public static class Dependency {
        @JsonProperty("pkg")
        private PackageInfo pkg;

        @Data
        public static class PackageInfo {
            private String name;
            private String ecosystem;
        }
    }

    @Data
    public static class SecurityVulnerability {
        private String severity;
        private String description;
        private String ghsaId;
        private String cveId;
    }
}
