package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.Usuario;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapaManagerTest {
    MapaManager mm;

    @Before
    public void setUp(){
        this.mm = MapaManagerImpl.getInstance();
        this.mm.addPuntoInteres(1, 1, ElementType.DOOR);
        this.mm.addPuntoInteres(2, 2, ElementType.WALL);
        this.mm.addPuntoInteres(3, 3, ElementType.BRIDGE);
        this.mm.addUsuario("U1", "Usuario1", "BApellido1", "Email1", "Fecha1");
        this.mm.addUsuario("U2", "Usuario2", "CApellido2", "Email2", "Fecha2");
        this.mm.addUsuario("U3", "Usuario3", "Apellido3", "Email3", "Fecha3");
    }

    @After
    public void tearDown(){
        this.mm.clear();
    }

    @Test
    public void addUsuarioTest(){
        Assert.assertEquals(3, mm.size());

        this.mm.addUsuario("U4", "Usuario4", "Apellido4", "Email4", "Fecha4");

        Assert.assertEquals(4, mm.size());
    }

    @Test
    public void getUsuarioOrdenadoAlfabeticamenteTest(){
        Assert.assertEquals(3, mm.size());

        this.mm.getUsuarioOrdenadoAlfabeticamente();

        Assert.assertEquals("BApellido1", mm.getUsuario("U1").getApellido());
        Assert.assertEquals("CApellido2", mm.getUsuario("U2").getApellido());
        Assert.assertEquals("Apellido3", mm.getUsuario("U3").getApellido());
    }

    @Test
    public void addPuntoInteresTest(){
        PuntoInteres punto = mm.addPuntoInteres(4, 4, ElementType.DOOR);

        Assert.assertNotNull(punto);
        Assert.assertEquals(4, punto.getHorizontal());
        Assert.assertEquals(4, punto.getVertical());
        Assert.assertEquals(ElementType.DOOR, punto.getType());
    }

    @Test
    public void getPuntosInteresUsuarioTest(){
        mm.registrarPuntoUsuario("U1", 1, 1);
        mm.registrarPuntoUsuario("U1", 2, 2);
        mm.registrarPuntoUsuario("U1", 3, 3);

        Assert.assertEquals(3, mm.getPuntosInteresUsuario("U1").size());
    }

    @Test
    public void getUsuariosHanPasadoPorAquiTest(){
        mm.registrarPuntoUsuario("U1", 1, 1);
        mm.registrarPuntoUsuario("U2", 1, 1);
        mm.registrarPuntoUsuario("U3", 1, 1);

        Assert.assertEquals(3, mm.getUsuariosHanPasadoPorAqui(1, 1).size());
    }



}
