import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { BodyComponent } from './body/body.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { StanzeComponent } from './stanze/stanze.component';
import { DialogAggiungiPazienteManualeComponent } from './dialog-aggiungi-paziente-manuale/dialog-aggiungi-paziente-manuale.component';
import { FormsModule } from '@angular/forms';
import { PazientiComponent } from './pazienti/pazienti.component';
import { DialogVisualizzaPazienteComponent } from './dialog-visualizza-paziente/dialog-visualizza-paziente.component';
import { HttpClientModule } from '@angular/common/http';
import { DialogScannerizzaPazienteComponent } from './dialog-scannerizza-paziente/dialog-scannerizza-paziente.component';

import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { DialogVisualizzaLettoComponent } from './dialog-visualizza-letto/dialog-visualizza-letto.component';
import { HelpComponent } from './help/help.component';
import { HeaderLogoutComponent } from './header-logout/header-logout.component';

import { HammerModule } from "@angular/platform-browser";

import { IgxDatePickerModule } from 'igniteui-angular';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { DialogVisualizzaLettiLiberiComponent } from './dialog-visualizza-letti-liberi/dialog-visualizza-letti-liberi.component';
import { DialogConfermaComponent } from './dialog-conferma/dialog-conferma.component';
import { DialogPazienteNonPresenteComponent } from './dialog-paziente-non-presente/dialog-paziente-non-presente.component';
import { DialogPazienteGiaPresenteComponent } from './dialog-paziente-gia-presente/dialog-paziente-gia-presente.component';
import { DialogLettiOccupatiComponent } from './dialog-letti-occupati/dialog-letti-occupati.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BodyComponent,
    LoginComponent,
    PageNotFoundComponent,
    StanzeComponent,
    DialogAggiungiPazienteManualeComponent,
    PazientiComponent,
    DialogVisualizzaPazienteComponent,
    DialogScannerizzaPazienteComponent,
    DialogVisualizzaLettoComponent,
    HelpComponent,
    HeaderLogoutComponent,
    DialogVisualizzaLettiLiberiComponent,
    DialogConfermaComponent,
    DialogPazienteNonPresenteComponent,
    DialogPazienteGiaPresenteComponent,
    DialogLettiOccupatiComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    HttpClientModule,
    ZXingScannerModule,
    HammerModule,
    IgxDatePickerModule,
    MatNativeDateModule,
    MatDatepickerModule,
  ],
  providers: [MatDatepickerModule, MatNativeDateModule],
  entryComponents: [DialogAggiungiPazienteManualeComponent, DialogScannerizzaPazienteComponent],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
