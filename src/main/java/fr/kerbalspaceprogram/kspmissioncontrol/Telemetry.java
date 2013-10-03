/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.kerbalspaceprogram.kspmissioncontrol;

import java.net.DatagramPacket;

/**
 *
 * @author Bruno
 */
public class Telemetry {
    
    public enum DataTelemetry {uid, name, missionTime, referenceBody, altitude, latitude, longitude, verticalSpeed, obt_velocity, srf_velocity, ApA, PeA, eccentricity, totalMass, atmDensity, temperature, dynamicPressure, staticPressure;}
    public static final String[] DATA_LABEL = {"Unique id", "Vessel name", "Mission time", "Reference body", "Altitude", "Latitude", "Longitude", "Vertical speed", "Orbital velocity", "Surface velocity", "Apoapsis", "Periapsis", "Eccentricity", "Total mass", "Atmopshere density", "Temperature", "Static pressure", "Dynamic pressure"};

    private static final String SEPARATOR = ",";
    private String[] data;
    
    Telemetry(DatagramPacket datagramPacket) {
        
        // Le truc trop idiot : convertir un tableau de byte en char... on est content -_-"
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<datagramPacket.getLength(); i++)
            sb.append((char)datagramPacket.getData()[i]);
        
        String[] s = sb.toString().split(Telemetry.SEPARATOR);
        
        this.data = new String[DataTelemetry.values().length];
        for (int i=0; i<this.data.length; i++) {
            this.data[i] =s[i];
        }
    }
    
    public String getData(DataTelemetry dataTelemetry) {
        return this.data[dataTelemetry.ordinal()];
    }
    
    public static String getDataLabel(DataTelemetry dataTelemetry) {
        return DATA_LABEL[dataTelemetry.ordinal()];
    }
    
    public static Class typeOf(DataTelemetry dataTelemetry) {
        if (dataTelemetry.equals(DataTelemetry.uid) || dataTelemetry.equals(DataTelemetry.name) || dataTelemetry.equals(DataTelemetry.referenceBody))
            return String.class;
        else
            return float.class;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (int i=0; i<this.data.length; i++) {
            sb.append(this.data[i]);
            if (i < this.data.length-1) sb.append(Telemetry.SEPARATOR);      
        }
        
        return sb.toString();
    }
}
