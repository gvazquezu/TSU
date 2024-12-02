import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ResponsableService } from 'src/app/services/tarea/responsable.service';
import { DepartamentoService } from 'src/app/services/tarea/departamento.service';
import { PuestoService } from 'src/app/services/tarea/puesto.service';

@Component({
  selector: 'app-responsable',
  templateUrl: './responsable.component.html',
  styleUrls: ['./responsable.component.css']
})
export class ResponsableComponent implements OnInit {
  responsableForm!: FormGroup;
  responsables: any[] = [];
  departamentos: any[] = [];
  puestos: any[] = [];
  responsableSeleccionado: any = null;

  constructor(
    private fb: FormBuilder,
    private responsableService: ResponsableService,
    private departamentoService: DepartamentoService,
    private puestoService: PuestoService
  ) {}

  ngOnInit(): void {
    this.inicializarFormulario();
    this.cargarResponsables();
    this.cargarDepartamentos();
    this.cargarPuestos();
  }

  // Inicializa el formulario reactivo
  inicializarFormulario(): void {
    this.responsableForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      celular: ['', Validators.required],
      departamentoId: ['', Validators.required],
      puestoId: ['', Validators.required]
    });
  }

  // Carga la lista de responsables desde el servicio
  cargarResponsables(): void {
    this.responsableService.getAllResponsable().subscribe(
      (data) => (this.responsables = data),
      (error) => console.error('Error al cargar responsables', error)
    );
  }

  // Carga la lista de departamentos desde el servicio
  cargarDepartamentos(): void {
    this.departamentoService.getAllDepartamento().subscribe(
      (data) => (this.departamentos = data),
      (error) => console.error('Error al cargar departamentos', error)
    );
  }

  // Carga la lista de puestos desde el servicio
  cargarPuestos(): void {
    this.puestoService.getAllPuesto().subscribe(
      (data) => (this.puestos = data),
      (error) => console.error('Error al cargar puestos', error)
    );
  }

  // Guarda o actualiza un responsable
  guardar(): void {
    if (this.responsableSeleccionado) {
      // Actualizar responsable
      this.responsableService
        .updateResponsable(this.responsableSeleccionado.id, this.responsableForm.value)
        .subscribe(() => {
          this.cargarResponsables();
          this.limpiarFormulario();
        });
    } else {
      // Crear nuevo responsable
      this.responsableService.createResponsable(this.responsableForm.value).subscribe(() => {
        this.cargarResponsables();
        this.limpiarFormulario();
      });
    }
  }

  // Selecciona un responsable para editar
  seleccionarResponsable(responsable: any): void {
    this.responsableSeleccionado = responsable;
    this.responsableForm.patchValue(responsable);
  }

  // Elimina un responsable por ID
  eliminarResponsable(id: number): void {
    if (confirm('¿Está seguro de eliminar este responsable?')) {
      this.responsableService.deleteResponsable(id).subscribe(() => {
        this.cargarResponsables();
      });
    }
  }

  // Limpia el formulario y deselecciona el responsable
  limpiarFormulario(): void {
    this.responsableForm.reset();
    this.responsableSeleccionado = null;
  }
}