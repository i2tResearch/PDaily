import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

// Routes
import { APP_ROUTING } from './app.routes';

// Services

// Components
import { AppComponent } from './app.component';
import {DiagnosticComponent} from './diagnostic_table/diagnostic.component';
import {DiagnosticGridComponent} from './diagnostic_grid/grid.component';
import {GeneralComponent} from './general_grid/general.component';


@NgModule({
  declarations: [
    AppComponent,
    DiagnosticComponent,
    DiagnosticGridComponent,
    GeneralComponent
  ],
  imports: [
    BrowserModule,
    APP_ROUTING
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
