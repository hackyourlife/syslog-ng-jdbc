suite = {
  "mxversion" : "5.55.0",
  "name" : "syslog-ng-jdbc",
  "versionConflictResolution" : "latest",

  "javac.lint.overrides" : "none",

  "licenses" : {
    "GPLv3" : {
      "name" : "GNU General Public License, version 3",
      "url" : "https://opensource.org/licenses/GPL-3.0",
    }
  },

  "libraries" : {
    "SYSLOGNG" : {
      "path" : "lib/syslog_ng.jar",
      "urls" : [
        "https://orakel.has.enough.coffee/repo/syslog_ng.jar"
      ],
      "sha1" : "c76201a9e24eb6c1b54cc73c0be46be983627a86",
      "maven" : {
        "groupId" : "syslog-ng",
        "artifactId" : "java",
        "version" : "1.0.0",
      }
    }
  },

  "defaultLicense" : "GPLv3",

  "projects" : {

    "com.everyware.syslog_ng" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "checkstyle" : "com.everyware.syslog_ng",
      "dependencies" : [
        "SYSLOGNG"
      ],
      "javaCompliance" : "1.8",
      "workingSets" : "syslog-ng-jdbc",
      "license" : "GPLv3",
    },

    "com.everyware.syslog_ng.test" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "checkstyle" : "com.everyware.syslog_ng",
      "dependencies" : [
        "com.everyware.syslog_ng",
        "mx:JUNIT"
      ],
      "javaCompliance" : "1.8",
      "workingSets" : "syslog-ng-jdbc",
      "license" : "GPLv3",
    }

  },

  "distributions" : {
    "SYSLOGJDBC" : {
      "path" : "build/syslog_ng_jdbc.jar",
      "subDir" : "syslog-ng-jdbc",
      "sourcesPath" : "build/syslog_ng_jdbc.src.zip",
      "dependencies" : [
        "com.everyware.syslog_ng"
      ],
      "exclude": [
        "SYSLOGNG",
      ]
    },

    "SYSLOGJDBC_TEST" : {
      "path" : "build/syslog_ng_jdbc_test.jar",
      "subDir" : "syslog-ng-jdbc",
      "sourcesPath" : "build/syslog_ng_jdbc_test.src.zip",
      "dependencies" : [
        "com.everyware.syslog_ng.test"
      ],
      "distDependencies" : [
        "SYSLOGJDBC"
      ],
      "exclude": [
        "mx:JUNIT"
      ]
    }
  }
}
