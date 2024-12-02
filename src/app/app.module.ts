import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ResponsableComponent } from './components/responsable/responsable.component';
import { ProyectoComponent } from './components/proyecto/proyecto.component';
import { DepartamentoComponent } from './components/departamento/departamento.component';
import { PuestoComponent } from './components/puesto/puesto.component';
import { TareaComponent } from './components/tarea/tarea.component';

import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { TareaService } from './services/tarea/tarea.service';
import { ResponsableService } from './services/tarea/responsable.service';
import { ProyectoService } from './services/tarea/proyecto.service';
import { DepartamentoService } from './services/tarea/departamento.service';
import { PuestoService } from './services/tarea/puesto.service';


@NgModule({
  declarations: [
    AppComponent,
    TareaComponent,
    ResponsableComponent,
    ProyectoComponent,
    DepartamentoComponent,
    PuestoComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [TareaService, 
              ResponsableService, 
              ProyectoService, 
              DepartamentoService, 
              PuestoService
            ],
  bootstrap: [AppComponent, ResponsableComponent]
})
export class AppModule { }
