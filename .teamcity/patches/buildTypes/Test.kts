package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Test'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Test")) {
    vcs {
        add(DslContext.settingsRoot.id!!)
    }

    expectSteps {
    }
    steps {
        insert(0) {
            script {
                scriptContent = "dir /b /s | sort"
            }
        }
    }

    features {
        add {
            freeDiskSpace {
                requiredSpace = "30gb"
                failBuild = false
            }
        }
    }
}
