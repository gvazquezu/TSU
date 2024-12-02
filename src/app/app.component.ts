import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Prioridad, Estado } from './enums/tarea.enum'; // Importar los ENUMs
import { TareaService } from "./services/tarea/tarea.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  
  tareaForm!: FormGroup;
  prioridades = Object.values(Prioridad); // Cargar las opciones del ENUM
  estados = Object.values(Estado); // Cargar las opciones del ENUM
  tarea: any[] =[];
  filteredTarea: any[] = [];
  tareaSeleccionada: any = null;


  constructor(
    public fb: FormBuilder,
    public tareaService: TareaService){ }

  ngOnInit(): void {
    
    this.tareaForm = this.fb.group({
      
      nombre : ['', Validators.required],
      prioridad : ['', Validators.required],
      responsableId : ['', Validators.required],
      estado : ['', Validators.required],
      fechaRegistro: ['', Validators.required],
      fechaCierre: ['', Validators.required],
      proyectoId : ['', Validators.required]

    });

    this.cargarTareas();
  }

  cargarTareas(): void {
    this.tareaService.getAllTarea().subscribe(resp => {
      this.tarea = resp;
      this.filteredTarea = this.tarea;
    },
    error => { console.error(error); });
  }

  guardar(): void {
    if (this.tareaSeleccionada) {
      
      this.tareaService.updateTarea(this.tareaSeleccionada.id, this.tareaForm.value).subscribe(resp => {
        console.log('Tarea actualizada', resp);
        this.cargarTareas();
        this.limpiarFormulario();
      }, error => {console.error(error);});
    
    } else {
      
      this.tareaService.createTarea(this.tareaForm.value).subscribe(resp => {
        console.log('Tarea creada', resp);
        this.cargarTareas();
        this.limpiarFormulario();
      }, error => {console.error(error);});
    }
  }

  limpiarFormulario(): void {
    this.tareaForm.reset();
    this.tareaSeleccionada = null;
  }

  seleccionarTarea(tarea: any): void {
    this.tareaSeleccionada = tarea;
    this.tareaForm.patchValue(tarea); 
  }

  eliminarTarea(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta tarea?')) {
      this.tareaService.deleteTarea(id).subscribe(resp => {
        console.log('Tarea eliminada', resp);
        this.cargarTareas(); // Recargar la lista de tareas
      }, error => {console.error(error);});
    }
  }
}
 

 /* formatDate(dateString: string): string {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }

  searchFilters = {
    nombreTarea: '',
    responsable: '',
    proyecto: '',
    departamento: '',
    puesto: ''
  };
  
  buscarTareas(): void {
    // Usar el método del servicio para buscar tareas usando filtros
    this.tareaService.getFilteredTareas(this.searchFilters).subscribe(resp => {
      this.filteredTarea = resp;
    }, error => {
      console.error(error);
    });
  }
  
  limpiarBusqueda(): void {
    this.searchFilters = {
      nombreTarea: '',
      responsable: '',
      proyecto: '',
      departamento: '',
      puesto: ''
    };
    this.cargarTareas(); // Recarga todas las tareas sin filtros
  }*/

