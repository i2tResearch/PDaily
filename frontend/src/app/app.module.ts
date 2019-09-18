import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule} from '@angular/platform-browser/animations';

// Routes
import { APP_ROUTING } from './app.routes';

// Services

// Components
import { AppComponent } from './app.component';
// Imported syncfusion button module from buttons package
import { ButtonModule } from '@syncfusion/ej2-angular-buttons';
import { SidebarModule } from '@syncfusion/ej2-angular-navigations';
import { SidebarPDaylyComponent } from './components/share/sidebar/sidebar.component';
import { DiagnosticTableComponent } from './components/diagnostic-table/diagnostic-table.component';
import { DiagnosticBannerComponent } from './components/diagnostic-banner/diagnostic-banner.component';
import { TreeViewModule } from '@syncfusion/ej2-angular-navigations';



@NgModule({
  declarations: [
    AppComponent,
    SidebarPDaylyComponent,
    DiagnosticTableComponent,
    DiagnosticBannerComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    APP_ROUTING,

    // Registering EJ2 Button Module
    ButtonModule,
    SidebarModule,
    TreeViewModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
