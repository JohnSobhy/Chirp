
import org.gradle.api.Project
import com.android.build.api.dsl.CommonExtension
import com.john_halaka.chirp.convention.libs
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
 with(commonExtension) {
     buildFeatures {
         compose = true
     }

     dependencies {
         val bom = libs.findLibrary("androidx-compose-bom").get()
         "implementation"(platform(bom))
         "testImplementation"(platform(bom))
         "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
         "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling-preview").get())


     }
 }
}

