import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TareaService } from 'src/app/services/tarea/tarea.service';
import { ResponsableService } from '../../services/tarea/responsable.service';
import { ProyectoService } from '../../services/tarea/proyecto.service';
import { Estado, Prioridad } from 'src/app/enums/tarea.enum';

@Component({
  selector: 'app-tarea',
  templateUrl: './tarea.component.html',
  styleUrls: ['./tarea.component.css']
})
export class TareaComponent implements OnInit {
 
  tareaForm!: FormGroup;
  tareas: any[] = [];
  responsables: any[] = []; // Definir responsables
  proyectos: any[] = []; // Definir proyectos
  tareaSeleccionada: any = null;

  prioridades = Object.values(Prioridad); // Array dinámico de prioridades
  estados = Object.values(Estado); // Array dinámico de estados

  constructor(
    private fb: FormBuilder,
    private tareaService: TareaService,
    private responsableService: ResponsableService,
    private proyectoService: ProyectoService
  ) { }

  ngOnInit(): void {
    this.tareaForm = this.fb.group({
      nombre: ['', Validators.required],
      prioridad: ['', Validators.required],
      responsableId: ['', Validators.required],
      estado: ['', Validators.required],
      fechaRegistro: ['', Validators.required],
      fechaCierre: [''],
      proyectoId: ['', Validators.required]
    });

    this.cargarTareas();
    this.cargarResponsables();
    this.cargarProyectos();
  }

  cargarTareas(): void {
    this.tareaService.getAllTarea().subscribe(
      resp => this.tareas = resp,
      error => console.error(error)
    );
  }

  cargarResponsables(): void {
    this.responsableService.getAllResponsable().subscribe(
      resp => this.responsables = resp,
      error => console.error(error)
    );
  }

  cargarProyectos(): void {
    this.proyectoService.getAllProyecto().subscribe(
      resp => this.proyectos = resp,
      error => console.error(error)
    );
  }

  guardar(): void {
    if (this.tareaForm.invalid) {
      console.error('Formulario inválido');
      return;
    }
  
    const nuevaTarea = this.tareaForm.value;
  
    if (this.tareaSeleccionada) {
      // Actualizar tarea existente
      this.tareaService.updateTarea(this.tareaSeleccionada.id, nuevaTarea).subscribe(
        () => {
          console.log('Tarea actualizada con éxito');
          this.cargarTareas();
          this.limpiarFormulario();
        },
        error => console.error('Error al actualizar la tarea:', error)
      );
    } else {
      // Crear nueva tarea
      this.tareaService.createTarea(nuevaTarea).subscribe(
        () => {
          console.log('Tarea creada con éxito');
          this.cargarTareas();
          this.limpiarFormulario();
        },
        error => console.error('Error al crear la tarea:', error)
      );
    }
  }
  

  seleccionarTarea(tarea: any): void {
    this.tareaSeleccionada = tarea;
    this.tareaForm.patchValue(tarea);
  }

  eliminarTarea(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta tarea?')) {
      this.tareaService.deleteTarea(id).subscribe(
        resp => {
          console.log('Tarea eliminada', resp);
          this.cargarTareas();
        },
        error => console.error(error)
      );
    }
  }

  limpiarFormulario(): void {
    this.tareaForm.reset();
    this.tareaSeleccionada = null;
  }
}
