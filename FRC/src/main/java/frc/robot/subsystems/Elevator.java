package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Elevator {
    public final SparkMax elevatorMotor1;
    public final SparkMax elevatorMotor2;

    public Elevator()
    {
        elevatorMotor1 = new SparkMax(OperatorConstants.elevatorMotor1ID, MotorType.kBrushless);
        elevatorMotor2 = new SparkMax(OperatorConstants.elevatorMotor2ID, MotorType.kBrushless);
        
        //Leader & follower system is not really necessary for the elevator given the setSpeed method
        
    }
    public void setSpeed(double speed)
    {
        //Inverse speed to adjust for joystick input
        elevatorMotor1.set(-speed);
        elevatorMotor2.set(-speed);
    }
}
