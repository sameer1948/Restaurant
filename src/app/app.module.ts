import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
<<<<<<< HEAD
import { AppRoutingModule } from './app-routing.module';
=======
>>>>>>> 4f3518b2833645760d0b259d2520a7258d14dbe2

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
<<<<<<< HEAD
    HttpModule,
    AppRoutingModule
=======
    HttpModule
>>>>>>> 4f3518b2833645760d0b259d2520a7258d14dbe2
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
