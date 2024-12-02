import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PuestoService {
  private API_SERVER = 'http://localhost:8080/api/puesto';

  constructor(private httpClient: HttpClient) {}

  getAllPuesto(): Observable<any> {
    return this.httpClient.get(this.API_SERVER);
  }

  createPuesto(puesto: any): Observable<any> {
    return this.httpClient.post(this.API_SERVER, puesto);
  }

  updatePuesto(id: number, puesto: any): Observable<any> {
    return this.httpClient.put(`${this.API_SERVER}/${id}`, puesto);
  }

  deletePuesto(id: number): Observable<any> {
    return this.httpClient.delete(`${this.API_SERVER}/${id}`);
  }

  public getFilteredPuestos(filters: any): Observable<any[]> {
    let params = new HttpParams();
    if (filters.nombre) params = params.set('nombre', filters.nombre);

    return this.httpClient.get<any[]>(`${this.API_SERVER}/filtrar`, { params });
  }
}
