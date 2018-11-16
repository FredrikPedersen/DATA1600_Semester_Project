package goty.controllers;

import net.java.games.input.Component;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Controller;

/**
 * <h1>Gamepad</h1>
 * <p>Klasse som muligjør støtte for en gamepad/spillkontroller i spillet. Kun testet med Xbox 360 og Xbox One-kontrollere.</p>
 *
 *@author Ole Østvold
 *@version 1.0
 *@since 13. mai 2018
 */
public class Gamepad {
    
    /** Hvorvidt en gamepad er koblet til */
    public static boolean isGamepadConnected;
    
    /** Kontrolleren */
    private static Controller activeController;
    
    /** Tabell med alle kontrollerens komponenter (knapper og spaker) */
    private static Component[] components;

    // komponenter som brukes i spillet
    private Component zAxisComponent;
    private Component xAxisComponent;
    private Component yAxisComponent;
    private Component rxAxisComponent;
    private Component ryAxisComponent;
    
    /**
     * Verdien til gamepadens Z-akse-komponent (varierer fra -1 til 1)
     */
    public static float zAxis;
    
    /**
     * Verdien til gamepadens X-akse-komponent (varierer fra -1 til 1)
     */
    public static float xAxis;
    
    /**
     * Verdien til gamepadens Y-akse-komponent (varierer fra -1 til 1)
     */
    public static float yAxis;
    
    /**
     * Verdien til gamepadens RX-akse-komponent (varierer fra -1 til 1)
     */
    public static float rxAxis;
    
    /**
     * Verdien til gamepadens RY-akse-komponent (varierer fra -1 til 1)
     */
    public static float ryAxis;
    
    private final float DEADZONE = 0.2f;
    
    /**
     * Konstruerer et gamepad-objekt. Setter i gang en metode som sjekker om
     * en gamepad er tilkoblet.
     */
    public Gamepad() {   
        searchForController();
        if (activeController == null) System.out.println("No gamepad detected.");
    }

    private void searchForController() {
        
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.GAMEPAD) {
                activeController = controller;
                components = activeController.getComponents();
                isGamepadConnected = true;
                initializeComponents();
                return;
            }
        }
    }


    private void initializeComponents() {
        for (Component c : components) {
            
            if (c.getIdentifier() == Component.Identifier.Axis.X) {
                xAxisComponent = c;
                continue;
            }
            if (c.getIdentifier() == Component.Identifier.Axis.Y)  {
                yAxisComponent = c;
                continue;
            }
            if (c.getIdentifier() == Component.Identifier.Axis.Z) {
                zAxisComponent = c;
                continue;
            }
            if (c.getIdentifier() == Component.Identifier.Axis.RX) {
                rxAxisComponent = c;
                continue;
            }
            if (c.getIdentifier() == Component.Identifier.Axis.RY) {
                ryAxisComponent = c;
            }
        }
    }

    /**
     * Leser av verdier fra gamepaden og sjekker samtidig om gamepaden er
     * blitt frakoblet.
     */
    public void poll() {
        
        if (!activeController.poll() ) {
            isGamepadConnected = false;
            System.out.println("Gamepad disconnected.");
            return;
        }
        
        // leser av verdier
        if (xAxisComponent != null) xAxis = xAxisComponent.getPollData();
        if (yAxisComponent != null) yAxis = yAxisComponent.getPollData();
        if (zAxisComponent != null) zAxis = zAxisComponent.getPollData();
        if (rxAxisComponent != null) rxAxis = rxAxisComponent.getPollData();
        if (ryAxisComponent != null) ryAxis = ryAxisComponent.getPollData();
        
        // justerer for deadzone
        if (xAxis < DEADZONE && xAxis > -DEADZONE) xAxis = 0;
        if (yAxis < DEADZONE && yAxis > -DEADZONE) yAxis = 0;
        if (rxAxis < DEADZONE && rxAxis > -DEADZONE && ryAxis < DEADZONE && ryAxis > -DEADZONE) {
            rxAxis = 0;
            ryAxis = 0;
        }
    
    }

}
