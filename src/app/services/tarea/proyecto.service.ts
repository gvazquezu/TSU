import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProyectoService {
  private API_SERVER = 'http://localhost:8080/api/proyecto';

  constructor(private httpClient: HttpClient) {}

  getAllProyecto(): Observable<any> {
    return this.httpClient.get(this.API_SERVER);
  }

  createProyecto(proyecto: any): Observable<any> {
    return this.httpClient.post(this.API_SERVER, proyecto);
  }

  updateProyecto(id: number, proyecto: any): Observable<any> {
    return this.httpClient.put(`${this.API_SERVER}/${id}`, proyecto);
  }

  deleteProyecto(id: number): Observable<any> {
    return this.httpClient.delete(`${this.API_SERVER}/${id}`);
  }

  public getFilteredProyectos(filters: any): Observable<any[]> {
    let params = new HttpParams();
    if (filters.nombre) params = params.set('nombre', filters.nombre);
    if (filters.descripcion) params = params.set('descripcion', filters.descripcion);

    return this.httpClient.get<any[]>(`${this.API_SERVER}/filtrar`, { params });
  }
}
