#header()

package ${package}.commands;
#set($command = $helper.getByName($command_name, $robot))
#set($params = $command.getProperty("Parameters").getValue())
#macro( klass $cmd )#if( "#type($cmd)" == "" )CommandBase#else#type($cmd)#end#end
import edu.wpi.first.wpilibj2.command.CommandBase;
import ${package}.Robot;
#@autogenerated_code("imports", "")
#parse("${exporter_path}Command-imports.java")
#end

/**
 *
 */
public class #class($command.name) extends #klass($command) {

#@autogenerated_code("variable_declarations", "    ")
#parse("${exporter_path}Command-variable-declarations.java")
#end

#@autogenerated_code("constructor", "    ")
#parse("${exporter_path}Command-constructor.java")
#end
#@autogenerated_code("variable_setting", "        ")
#parse("${exporter_path}Command-variable-setting.java")
#end
#@autogenerated_code("requires", "        ")
#parse("${exporter_path}Command-requires.java")
#end
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }
}
