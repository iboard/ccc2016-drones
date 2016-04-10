Feature: Drone Take Off, Hover, and Land

  Scenario: Fly To And Hover
    Given A landed drone with id 0 at 0, 0, 0 (x,y,z)
    When Tell drone 0 to hover at 2,2,10.0
    And wait for 2000 ticks
    Then drone 0 should be between altitude 9.5 and 10.5
    And drone 0 should be at 2,2,10

  Scenario: Prepare Landing
    Given A flying drone with id 0 at 0, 0, 10 (x,y,z)
    When Tell drone 0 to hover at 0,0,0.3
    And wait for 20000 ticks
    Then drone 0 should be between altitude 0.1 and 0.4

  Scenario: Safe landing from below 0.5
    Given A flying drone with id 0 at 0, 0, 2 (x,y,z)
    When Tell drone 0 to hover at 0,0,0.3
    And wait for 60 ticks
    And Tell drone 0 to land
    Then drone 0 is landed

  Scenario: Unsafe landing (crash) when altitude is to high for landing
    Given A flying drone with id 0 at 0, 0, 1 (x,y,z)
    Then expect to crash when trying to land drone 0


