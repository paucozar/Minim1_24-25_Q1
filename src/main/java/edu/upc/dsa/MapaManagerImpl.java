package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.ElementType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class MapaManagerImpl implements MapaManager {
    private static MapaManager instance;
    private List<Usuario> usuarios;
    private List<PuntoInteres> puntosInteres;
    private Map<String, List<PuntoInteres>> usuarioPuntosInteres;
    final static Logger logger = Logger.getLogger(MapaManagerImpl.class);

    private MapaManagerImpl() {
        this.usuarios = new ArrayList<>();
        this.puntosInteres = new ArrayList<>();
        this.usuarioPuntosInteres = new HashMap<>();
    }

    public static MapaManager getInstance() {
        if (instance==null) instance = new MapaManagerImpl();
        return instance;
    }
    public int size() {
        int ret = this.usuarios.size();
        logger.info("size " + ret);

        return ret;
    }
    public Usuario addUsuario(String id, String nombre, String apellido, String email, String fechaNacimiento) {
        logger.info("addUsuario("+id+","+nombre+","+apellido+","+email+","+fechaNacimiento+")");

        Usuario usunuevo = new Usuario(id, nombre, apellido, email, fechaNacimiento);
        this.usuarios.add(usunuevo);

        logger.info("Usuario added" + usunuevo);
        return usunuevo;
    }
    public List<Usuario> getUsuarioOrdenadoAlfabeticamente() {
        logger.info("getUsuarioOrdenadoAlfabeticamente");

        this.usuarios.sort((u1, u2) -> u1.getApellido().compareTo(u2.getApellido()));

        logger.info("Usuarios ordenados alfabeticamente" + this.usuarios);
        return this.usuarios;
    }
    public Usuario getUsuario(String id) {
        logger.info("getUsuario("+id+")");

        for (Usuario u: this.usuarios) {
            if (u.getId().equals(id)) {
                logger.info("Usuario encontrado" + u);
                return u;
            }
        }

        logger.warn("Usuario no encontrado");
        return null;
    }
    public PuntoInteres addPuntoInteres(int horizontal, int vertical, ElementType type) {
        logger.info("addPuntoInteres(" + horizontal + "," + vertical + "," + type + ")");
        try {
            PuntoInteres puntonuevo = new PuntoInteres(horizontal, vertical, type);
            this.puntosInteres.add(puntonuevo);
            logger.info("Punto de interes añadido" + puntonuevo);
            return puntonuevo;
        } catch (Exception e) {
            logger.error("Error adding point of interest: " + e.getMessage());
            logger.fatal("Fatal error adding point of interest: " + e.getMessage());
            return null;
        }
    }
    public void registrarPuntoUsuario(String id, int horizontal, int vertical) {
        logger.info("registrarPuntoUsuario(" + id + ", " + horizontal + ", " + vertical + ")");
        Usuario usuario = this.getUsuario(id);
        if (usuario == null) {
            logger.error("Usuario no encontrado: " + id);
            return;
        }
        for (PuntoInteres puntoInteres : this.puntosInteres) {
            if (puntoInteres.getHorizontal() == horizontal && puntoInteres.getVertical() == vertical) {
                this.usuarioPuntosInteres.get(id).add(puntoInteres);
                logger.info("Punto de interés registrado para el usuario: " + puntoInteres);
                return;
            }
        }
        logger.error("Punto de interés no encontrado en las coordenadas: (" + horizontal + ", " + vertical + ")");
    }
    public List<PuntoInteres> getPuntosInteresUsuario(String id) {
        logger.info("getPuntosInteresUsuario(" + id + ")");
        try {
            Usuario usuario = getUsuario(id);
            if (usuario == null) {
                logger.warn("User with ID " + id + " not found.");
                return null;
            }

            List<PuntoInteres> puntosInteresUsuario = usuario.getPuntosInteres();
            logger.info("Points of interest of user " + id + " retrieved");
            return puntosInteresUsuario;
        } catch (Exception e) {
            logger.error("Error getting points of interest for user: " + e.getMessage());
            logger.fatal("Fatal error getting points of interest for user: " + e.getMessage());
            return null;
        }
    }
    public List<Usuario> getUsuariosHanPasadoPorAqui(int horizontal, int vertical) {
        logger.info("getUsuariosHanPasadoPorAqui(" + horizontal + ", " + vertical + ")");
        try {
            List<Usuario> usuariosHanPasadoPorAqui = new ArrayList<>();
            for (Map.Entry<String, List<PuntoInteres>> lista : this.usuarioPuntosInteres.entrySet()) {
                for (PuntoInteres puntoInteres : lista.getValue()) {
                    if (puntoInteres.getHorizontal() == horizontal && puntoInteres.getVertical() == vertical) {
                        usuariosHanPasadoPorAqui.add(this.getUsuario(lista.getKey()));
                        break;
                    }
                }
            }

            logger.info("Users who have passed through point of interest (" + horizontal + ", " + vertical + ") retrieved");
            return usuariosHanPasadoPorAqui;
        } catch (Exception e) {
            logger.error("Error retrieving users who have passed through point of interest: " + e.getMessage());
            logger.fatal("Fatal error retrieving users who have passed through point of interest: " + e.getMessage());
            return null;
        }
    }
    public List<PuntoInteres> getPuntosInteresPorTipo(ElementType type) {
        logger.info("getPuntosInteresPorTipo(" + type + ")");

        List<PuntoInteres> puntosInteresPorTipo = new ArrayList<>();
        for (PuntoInteres puntoInteres : this.puntosInteres) {
            if (puntoInteres.getType() == type) {
                puntosInteresPorTipo.add(puntoInteres);
            }
        }

        logger.info("Points of interest of type " + type + " retrieved");
        return puntosInteresPorTipo;
    }
    public void clear() {
        logger.info("clear");

        this.usuarios.clear();
        this.puntosInteres.clear();
        this.usuarioPuntosInteres.clear();
    }

}
