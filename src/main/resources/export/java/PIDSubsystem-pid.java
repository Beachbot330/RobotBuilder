#set($subsystem = $helper.getByName($subsystem_name, $robot))
        super("#class($subsystem_name)", ${subsystem.getProperty("P").getValue()}, ${subsystem.getProperty("I").getValue()}, ${subsystem.getProperty("D").getValue()});

#if($subsystem.getProperty("Limit Input").getValue())
        getPIDController().setInputRange(${subsystem.getProperty("Minimum Input").getValue()}, ${subsystem.getProperty("Maximum Input").getValue()});
#end
#if($subsystem.getProperty("Limit Output").getValue())
        getPIDController().setOutputRange(${subsystem.getProperty("Minimum Output").getValue()}, ${subsystem.getProperty("Maximum Output").getValue()});
#end
        setAbsoluteTolerance(${subsystem.getProperty("Tolerance").getValue()});
        getPIDController().setContinuous(${subsystem.getProperty("Continuous").getValue()});

        SendableRegistry.setName(getPIDController(), "PIDSubsystem Controller");
        addChild(getPIDController());