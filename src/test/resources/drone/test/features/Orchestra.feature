Feature: Orchestrate many drones

  Scenario: Move 5 drones to different positions

    # Start with 5 drones at 0,0,0
    Given A landed drone with id 0 at 0, 0, 0 (x,y,z)
    And A landed drone with id 1 at 0, 0, 0 (x,y,z)
    And A landed drone with id 2 at 0, 0, 0 (x,y,z)
    And A landed drone with id 3 at 0, 0, 0 (x,y,z)
    And A landed drone with id 4 at 0, 0, 0 (x,y,z)

    # Set Targets to spread up the drones and fly them for 400 ticks
    When Tell drone 0 to hover at 1,1,1
    And Tell drone 1 to hover at 2,2,2
    And Tell drone 2 to hover at 3,3,3
    And Tell drone 3 to hover at 4,4,4
    And Tell drone 4 to hover at 5,5,5
    And wait for 400 ticks

    # Assert drones are spread up across the grid
    Then drone 0 should be at 1,1,1
    And drone 1 should be at 2,2,2
    And drone 2 should be at 3,3,3
    And drone 3 should be at 4,4,4
    And drone 4 should be at 5,5,5

  Scenario: Land 3 drones in a row

    # Start with 3 drones flying in the upper corner
    Given A flying drone with id 0 at 9, 9, 9 (x,y,z)
    And A flying drone with id 1 at 8, 8, 8 (x,y,z)
    And A flying drone with id 2 at 7, 7, 7 (x,y,z)

    # Move to landing position in the other corner
    When Tell drone 0 to hover at 1,1,0.3
    And Tell drone 1 to hover at 2,2,0.3
    And Tell drone 2 to hover at 3,3,0.3
    And wait for 128 ticks

    # Land the damn rot
    When Tell drone 0 to land
    And Tell drone 1 to land
    And Tell drone 2 to land
    And wait for 1 ticks

    # Assert position
    Then drone 0 should be at 1,1,0
    And drone 1 should be at 2,2,0
    And drone 2 should be at 3,3,0

    # Assert all landed
    Then drone 0 is landed
    And drone 1 is landed
    And drone 2 is landed

