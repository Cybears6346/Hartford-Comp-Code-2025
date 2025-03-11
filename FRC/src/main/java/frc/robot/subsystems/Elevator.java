package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Constants.OperatorConstants;

public class Elevator extends SubsystemBase {
    public final SparkMax elevatorMotor1;
    public final SparkMax elevatorMotor2;

    public Elevator()
    {
        elevatorMotor1 = new SparkMax(OperatorConstants.elevatorMotor1ID, MotorType.kBrushless);
        elevatorMotor2 = new SparkMax(OperatorConstants.elevatorMotor2ID, MotorType.kBrushless);
        SparkMaxConfig globalElevatorConfig = new SparkMaxConfig();
        SparkMaxConfig elevatorInvertedConfig = new SparkMaxConfig();

        globalElevatorConfig
            .smartCurrentLimit(50)
            .idleMode(IdleMode.kBrake); 
        elevatorInvertedConfig
            .apply(globalElevatorConfig)
            .inverted(true)
            .follow(elevatorMotor1);

        elevatorMotor1.configure(globalElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevatorMotor2.configure(elevatorInvertedConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }
    public void setSpeed(double speed)
    {
        //Inverse speed to adjust for joystick input.
        elevatorMotor1.set(-speed);
        elevatorMotor2.set(-speed);
    }
}