#header()
// ROBOTBUILDER TYPE: SequentialCommandGroup.

package ${package}.commands;
#set($command = $helper.getByName($command_name, $robot))
#set($params = $command.getProperty("Parameters").getValue())
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
#@autogenerated_code("imports", "")
#parse("${exporter_path}SequentialCommandGroup-imports.java")
#end

/**
 *
 */
public class #class($command.name) extends SequentialCommandGroup {

#@autogenerated_code("constructors", "    ")
#parse("${exporter_path}SequentialCommandGroup-constructors.java")
#end
    addCommands(
        // Add Commands here:
        // Also add parallel commands using the
        //
        // addCommands(
        //      new command1(argsN, subsystem),
        //      Commands.parallel(
        //          new command2(argsN, subsystem),
        //          new command3(argsN, subsystem)
        //      )    
        //  );

        );
    }

    @Override
    public boolean runsWhenDisabled() {
#@autogenerated_code("disabled", "        ")
#parse("${exporter_path}Command-disabled.java")
#end
    }
}
