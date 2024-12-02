import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResponsableService {

  private API_SERVER = 'http://localhost:8080/api/responsable';

  constructor(
    private httpClient: HttpClient
  ) { }

  getAllResponsable(): Observable<any> {
    return this.httpClient.get(`${this.API_SERVER}`);
  }

  getResponsableById(id: number): Observable<any> {
    return this.httpClient.get(`${this.API_SERVER}/${id}`);
  }

  createResponsable(responsable: any): Observable<any> {
    return this.httpClient.post(this.API_SERVER, responsable);
  }

  updateResponsable(id: number, responsable: any): Observable<any> {
    return this.httpClient.put(`${this.API_SERVER}/${id}`, responsable);
  }

  deleteResponsable(id: number): Observable<any> {
    return this.httpClient.delete(`${this.API_SERVER}/${id}`);
  }

  public getFilteredResponsables(filters: any): Observable<any[]> {
    let params = new HttpParams();
    if (filters.nombre) params = params.set('nombre', filters.nombre);
    if (filters.apellido) params = params.set('apellido', filters.apellido);
    if (filters.departamento) params = params.set('departamento', filters.departamento);
    if (filters.puesto) params = params.set('puesto', filters.puesto);

    return this.httpClient.get<any[]>(`${this.API_SERVER}/filtrar`, { params });
  }
}
