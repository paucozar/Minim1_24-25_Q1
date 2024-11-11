package edu.upc.dsa;

import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.ElementType;

import java.util.List;

public interface MapaManager {
    public Usuario addUsuario(String id, String nombre, String apellido, String email, String fechaNacimiento);
    public List<Usuario> getUsuarioOrdenadoAlfabeticamente();
    public Usuario getUsuario(String id);
    public PuntoInteres addPuntoInteres(int horizontal, int vertical, ElementType type);
    public Usuario registrarPuntoUsuario(String id, int horizontal, int vertical);
    public List<PuntoInteres> getPuntosInteresUsuario(String id);
    public List<Usuario> getUsuariosHanPasadoPorAqui(int horizontal, int vertical);
    public List<PuntoInteres> getPuntosInteresPorTipo(ElementType type);

    public void clear();
    public int size();
}
