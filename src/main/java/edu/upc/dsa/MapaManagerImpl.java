package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.ElementType;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class MapaManagerImpl implements MapaManager {
    private static MapaManager instance;
    private List<Usuario> usuarios;
    private List<PuntoInteres> puntosInteres;
    final static Logger logger = Logger.getLogger(MapaManagerImpl.class);

    private MapaManagerImpl() {
        this.usuarios = new ArrayList<>();
        this.puntosInteres = new ArrayList<>();
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

        logger.info("Usuario added");
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
                logger.info("Usuario encontrado");
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
            logger.info("Punto de interes añadido");
            return puntonuevo;
        } catch (Exception e) {
            logger.error("Error adding point of interest: " + e.getMessage());
            logger.fatal("Fatal error adding point of interest: " + e.getMessage());
            return null;
        }
    }
    public Usuario registrarPuntoUsuario(String id, int horizontal, int vertical) {
        logger.info("registrarPuntoUsuario(" + id + "," + horizontal + "," + vertical + ")");
        try {
            Usuario usuario = getUsuario(id);
            if (usuario == null) {
                logger.warn("User with ID " + id + " not found.");
                return null;
            }

            PuntoInteres puntoInteres = null;
            for (PuntoInteres punto : this.puntosInteres) {
                if (punto.getHorizontal() == horizontal && punto.getVertical() == vertical) {
                    puntoInteres = punto;
                    break;
                }
            }

            if (puntoInteres == null) {
                logger.warn("Point of interest at coordinates (" + horizontal + ", " + vertical + ") not found.");
                return null;
            }

            usuario.setPuntosInteres(puntosInteres);
            logger.info("User " + id + " registered at point of interest (" + horizontal + ", " + vertical + ")");
            return usuario;
        } catch (Exception e) {
            logger.error("Error registering user at point of interest: " + e.getMessage());
            logger.fatal("Fatal error registering user at point of interest: " + e.getMessage());
            return null;
        }
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
            for (Usuario usuario : this.usuarios) {
                for (PuntoInteres puntoInteres : usuario.getPuntosInteres()) {
                    if (puntoInteres.getHorizontal() == horizontal && puntoInteres.getVertical() == vertical) {
                        usuariosHanPasadoPorAqui.add(usuario);
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
    }

}
