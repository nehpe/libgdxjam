# Enemy AI behaviors

## Potential Enemy AI behavior loop

* Am I currently attacking?
	* Yes - Keep Attacking
	* No - continue
* Am I currently behind attacked?
	* Yes - Fight back
	* No - continue
* Am I currently shopping?
	* Yes - interate shopping counter
		* Is shopping counter hit it's max?
			* Yes - Continue
			* No - Keep shopping
	* No - Continue
* Is there a player in my vision?
	* Yes - 
		* Is the player aggressive (towards someone of my own type)?
			* Yes - Fuck him up
			* No - Continue
	* No - Continue

* Did I hear a gun shot? (Was there a gunshot near me)
	* Yes - Go check it out and attack out of fear
	* No - Continue
* Find somewhere else to go shop
