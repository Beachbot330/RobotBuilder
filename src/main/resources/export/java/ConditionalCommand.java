#header()

package ${package}.commands;
#set($command = $helper.getByName($command_name, $robot))

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

/**
 *
 */
public class #class($command.name) extends ConditionalCommand {


#@autogenerated_code("constructor", "    ")
#parse("${exporter_path}ConditionalCommand-constructors.java")
#end
            false ////TODO: Auto Generated method stub
        );

    }


    @Override
    public boolean runsWhenDisabled() {
#@autogenerated_code("disabled", "        ")
#parse("${exporter_path}Command-disabled.java")
#end
    }

}
