#header()

#set($command = $helper.getByName($command-name, $robot))

\#include "Commands/#class($command.name).h"

#@autogenerated_code("constructor", "")
#parse("${exporter-path}TimedCommand-constructor.cpp")
#end

// Called just before this Command runs the first time
void #class($command.name)::Initialize() {

}

// Called repeatedly when this Command is scheduled to run
void #class($command.name)::Execute() {
}

// Called once after command times out
void #class($command.name)::End() {

}

// Called when another command which requires one or more of the same
// subsystems is scheduled to run
void #class($command.name)::Interrupted() {

}
