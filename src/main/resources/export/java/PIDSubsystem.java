#header()

package ${package}.subsystems;
#set($subsystem = $helper.getByName($subsystem_name, $robot))

import ${package}.commands.*;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
#@autogenerated_code("imports", "")
#parse("${exporter_path}Subsystem-imports.java")
#end

/**
 *
 */
public class #class($subsystem.name) extends PIDSubsystem {

#@autogenerated_code("constants", "    ")
private static final double kP = ${subsystem.getProperty("P").getValue()};
private static final double kI = ${subsystem.getProperty("I").getValue()};
private static final double kD = ${subsystem.getProperty("D").getValue()};
#parse("${exporter_path}Subsystem-constants.java")
#end

#@autogenerated_code("declarations", "    ")
#parse("${exporter_path}Subsystem-declarations.java")
private double m_setpoint;
#end

    // Initialize your subsystem here
    public #class($subsystem.name)() {
#@autogenerated_code("pid", "        ")
#parse("${exporter_path}PIDSubsystem-pid.java")
#end

#@autogenerated_code("constructors", "        ")
#parse("${exporter_path}Subsystem-constructors.java")
#end


        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }

    /**
   * The log method puts interesting information to the SmartDashboard.
   */
    public void log() {
        //SmartDashboard.putData("Wrist Angle", m_pot);
    }

    @Override
    public double getMeasurement() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

#@autogenerated_code("source", "        ")
#parse("${exporter_path}PIDSubsystem-source.java")
#end
    }

    @Override
    public void useOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

#@autogenerated_code("output", "        ")
#parse("${exporter_path}PIDSubsystem-output.java")
#end
    }

    /**
     * Returns the setpoint used by the PIDController.
     *
     * @return The setpoint for the PIDController.
     */
    @Override
    public double getSetpoint() {
        return m_setpoint;
    }

    /**
     * Sets the setpoint used by the PIDController.
     *
     * @param setpoint The setpoint for the PIDController.
     */
    public void setSetpoint(double setpoint) {
        m_setpoint = setpoint;
    }

#@autogenerated_code("cmdpidgetters", "    ")
#parse("${exporter_path}Subsystem-pidgetters.java")
#end

}
