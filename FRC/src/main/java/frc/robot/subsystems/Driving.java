// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SignalsConfig;
import com.revrobotics.config.BaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants.OperatorConstants;



public class Driving extends SubsystemBase {
  private final SparkMax leftFrontMotor = new SparkMax(OperatorConstants.driveMotor4ID, MotorType.kBrushless);
  private final SparkMax leftRearMotor = new SparkMax(OperatorConstants.driveMotor3ID, MotorType.kBrushless);
  private final SparkMax rightFrontMotor = new SparkMax(OperatorConstants.driveMotor2ID, MotorType.kBrushless);
  private final SparkMax rightRearMotor = new SparkMax(OperatorConstants.driveMotor1ID, MotorType.kBrushless);



  private final SysIdRoutine sysIdRoutine = new SysIdRoutine(new SysIdRoutine.Config(), new SysIdRoutine.Mechanism(voltage -> {
    leftFrontMotor.setVoltage(voltage);
    leftRearMotor.setVoltage(voltage);
    rightFrontMotor.setVoltage(voltage);
    rightRearMotor.setVoltage(voltage);

  }, null,this));  
    
  private final DifferentialDrive differentialDrive;
    /** Creates a new Driving subsystem. */
  public Driving() {
    SignalsConfig config1 = new SignalsConfig();
    
    config1
      .analogPositionAlwaysOn(true)
      .analogVelocityAlwaysOn(true)
      .analogVoltageAlwaysOn(true)
      .absoluteEncoderPositionAlwaysOn(true)
      .absoluteEncoderVelocityAlwaysOn(true)
      .primaryEncoderPositionAlwaysOn(true)
      .primaryEncoderVelocityAlwaysOn(true)
      .externalOrAltEncoderPositionAlwaysOn(true)
      .externalOrAltEncoderVelocityAlwaysOn(true);
    

    SparkMaxConfig globalLeaderConfig = new SparkMaxConfig();
    globalLeaderConfig
          .smartCurrentLimit(50)
          .idleMode(IdleMode.kBrake)
          .apply(config1);
    
    SparkMaxConfig leftFollowerConfig = new SparkMaxConfig();
    leftFollowerConfig
        .apply(globalLeaderConfig)      
        .inverted(true)
        .follow(leftFrontMotor)
        .apply(config1);
          
    SparkMaxConfig rightLeaderConfig = new SparkMaxConfig();
    rightLeaderConfig
        .apply(globalLeaderConfig)
        .inverted(true)
        .apply(config1);
    
    SparkMaxConfig rightFollowerConfig = new SparkMaxConfig();
    rightFollowerConfig
        .apply(globalLeaderConfig)
        .inverted(true)
        .follow(rightFrontMotor)
        .apply(config1);

    leftFrontMotor.configure(globalLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftRearMotor.configure(leftFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFrontMotor.configure(rightLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightRearMotor.configure(rightFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    
    
    differentialDrive = new DifferentialDrive(leftFrontMotor, rightFrontMotor);
    
    

    }

  /**
   * Arcade drive method to be called in commands folder
   */
    public void arcadeDrive(DoubleSupplier speed, DoubleSupplier rotation) {
        differentialDrive.arcadeDrive(-speed.getAsDouble(), -rotation.getAsDouble());
    }

    public void arcadeDrive(double speed, double rotation) {
      differentialDrive.arcadeDrive(speed, rotation);
  }


    @Override
    public void periodic() {
        differentialDrive.feed();
        // This method will be called once per scheduler run
    }  

    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
      return sysIdRoutine.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
      return sysIdRoutine.dynamic(direction);
    }
}
