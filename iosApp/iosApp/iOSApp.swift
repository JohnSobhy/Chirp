import SwiftUI
import ComposeApp


@main struct iOSApp: App {
    init() {
        InitKoinKt.doInit()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}