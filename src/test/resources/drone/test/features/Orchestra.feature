Feature: Orchestrate ten drones

  Scenario: Move 5 drones to different positions
    Given A landed drone with id 0 at 0, 0, 0 (x,y,z)
    And A landed drone with id 1 at 0, 0, 0 (x,y,z)
    And A landed drone with id 2 at 0, 0, 0 (x,y,z)
    And A landed drone with id 3 at 0, 0, 0 (x,y,z)
    And A landed drone with id 4 at 0, 0, 0 (x,y,z)
    When Tell drone 0 to hover at 1,1,1
    And Tell drone 1 to hover at 2,2,2
    And Tell drone 2 to hover at 3,3,3
    And Tell drone 3 to hover at 4,4,4
    And Tell drone 4 to hover at 5,5,5
    And wait for 400 ticks
    Then drone 0 should be at 1,1,0.85
    And drone 1 should be at 2,2,2
    And drone 2 should be at 3,3,3
    And drone 3 should be at 4,4,3.85
    And drone 4 should be at 5,5,5
