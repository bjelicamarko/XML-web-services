import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RootLayoutPageComponent } from './pages/root-layout-page/root-layout-page.component';
import { SharedModule } from '../shared/shared.module';
import { HeaderCommonComponent } from './components/header/header-common/header-common.component';
import { MaterialExampleModule } from 'src/material.module';
import { AuthModule } from '../auth/auth.module';
import { HeaderMedicalOfficialComponent } from './components/header/header-medical-official/header-medical-official.component';

@NgModule({
    declarations: [
        AppComponent,
        RootLayoutPageComponent,
        HeaderCommonComponent,
        HeaderMedicalOfficialComponent
    ],
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        AuthModule,
        MaterialExampleModule,
        FormsModule,
        HttpClientModule,
        // ToastrModule.forRoot(),
        SharedModule,
        ReactiveFormsModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
