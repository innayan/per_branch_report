import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.ProjectReportTab
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.buildReportTab
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.projectReportTab

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.2"

project {

    buildType(Test)
    buildType(ConfigWithBranches)

    params {
        param("test", "te")
    }

    features {
        feature {
            id = "KEEP_RULE_50"
            type = "keepRules"
            param("limit.type", "NDaysSinceLastBuild")
            param("keepData.2.type", "artifacts")
            param("ruleDisabled", "false")
            param("limit.daysCount", "30")
            param("keepData.5.type", "statistics")
            param("filters.1.type", "branchActivity")
            param("partitions.1.type", "perBranch")
            param("preserveArtifacts", "true")
            param("keepData.4.type", "history")
            param("keepData.1.type", "everything")
            param("keepData.2.artifactsPatterns", "+:**/*")
            param("filters.1.activity", "inactive")
            param("keepData.3.type", "logs")
        }
        buildReportTab {
            id = "PROJECT_EXT_69"
            title = "config test"
            startPage = "test.html"
        }
        projectReportTab {
            id = "PROJECT_EXT_70"
            title = "test"
            startPage = "test.html"
            buildType = "${ConfigWithBranches.id}"
            sourceBuildRule = ProjectReportTab.SourceBuildRule.LAST_FINISHED
            param("revisionRuleBranchFilter", "-:*")
        }
    }
}

object ConfigWithBranches : BuildType({
    name = "config with branches"

    artifactRules = """
        aaa.txt
        subdir1 => subdir1
        src => src
        New Text Document.txt
        *.html
    """.trimIndent()

    vcs {
        root(AbsoluteId("TestArtifacts_HttpsGithubComInnayanMyrepositoryRefsHeadsMaster"))
    }

    steps {
        maven {
            goals = "package"
        }
    }

    features {
        feature {
            type = "keepRules"
            param("limit.type", "lastNBuilds")
            param("keepData.2.type", "artifacts")
            param("keepData.4.type", "history")
            param("keepData.1.type", "everything")
            param("keepData.2.artifactsPatterns", "+:**/*")
            param("ruleDisabled", "false")
            param("keepData.5.type", "statistics")
            param("keepData.3.type", "logs")
            param("limit.buildsCount", "3")
            param("preserveArtifacts", "true")
        }
        feature {
            type = "keepRules"
            param("ruleDisabled", "true")
        }
        feature {
            type = "keepRules"
            param("ruleDisabled", "true")
        }
        feature {
            type = "keepRules"
            param("ruleDisabled", "true")
        }
        feature {
            type = "keepRules"
            param("ruleDisabled", "true")
        }
        feature {
            type = "keepRules"
            param("limit.type", "lastNBuilds")
            param("keepData.2.type", "history")
            param("keepData.1.type", "logs")
            param("ruleDisabled", "false")
            param("filters.1.pattern", """
                +:refs/heads/new1
                +:refs/heads/new-branch
                +:refs/heads/testIYa
            """.trimIndent())
            param("keepData.3.type", "statistics")
            param("filters.1.type", "branchSpecs")
            param("partitions.1.type", "perBranch")
            param("limit.buildsCount", "51")
            param("preserveArtifacts", "true")
        }
        feature {
            type = "keepRules"
            param("limit.type", "lastNBuilds")
            param("keepData.2.type", "artifacts")
            param("filters.2.tagsList", "test")
            param("ruleDisabled", "false")
            param("keepData.5.type", "statistics")
            param("filters.1.type", "branchSpecs")
            param("preserveArtifacts", "true")
            param("filters.2.type", "tags")
            param("keepData.4.type", "history")
            param("keepData.1.type", "everything")
            param("keepData.2.artifactsPatterns", "+:**/*")
            param("filters.1.pattern", "+:refs/heads/te*")
            param("keepData.3.type", "logs")
            param("limit.buildsCount", "7")
        }
        feature {
            type = "keepRules"
            param("limit.type", "all")
            param("keepData.2.type", "artifacts")
            param("keepData.4.type", "history")
            param("keepData.1.type", "everything")
            param("keepData.2.artifactsPatterns", "+:**/*")
            param("ruleDisabled", "false")
            param("keepData.5.type", "statistics")
            param("keepData.3.type", "logs")
            param("preserveArtifacts", "true")
        }
    }
})

object Test : BuildType({
    name = "Test"
})
