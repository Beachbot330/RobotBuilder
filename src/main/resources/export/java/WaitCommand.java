#header()

package ${package}.commands;
#set($command = $helper.getByName($command_name, $robot))

import edu.wpi.first.wpilibj2.command.WaitCommand;
import ${package}.Robot;
#@autogenerated_code("imports", "")
#parse("${exporter_path}WaitCommand-imports.java")
#end

/**
 *
 */
public class #class($command.name) extends WaitCommand {

#@autogenerated_code("variable_declarations", "    ")
#parse("${exporter_path}Command-variable-declarations.java")
#end

#@autogenerated_code("constructor", "    ")
#parse("${exporter_path}WaitCommand-constructors.java")
#end
#@autogenerated_code("requires", "        ")
#parse("${exporter_path}Command-requires.java")
#end
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
#@autogenerated_code("initialize", "        ")
#parse("${exporter_path}WaitCommand-initialize.java")
#end
        super.initialize();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }


    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
#@autogenerated_code("end", "        ")
#parse("${exporter_path}WaitCommand-end.java")
#end
    }

}
