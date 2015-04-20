# sendash
Super Awesome way to manage health checks for VMWare, Palo Alto, network config, etc. and display captured data over time


# Notes

request from service includes:
* node identifier string (unique api key)
* timestamp of last received configuration

response from server includes an array of objects that were updated since the timestamp in the request:
* unique identifier of job, so the service knows if this is a net new job or one that will overwrite something it already has
* (name of job) not sure this is necessary, really - does the service ever need to know the name of the jobs it is running? possibly for error reporting?
* data (the powershell code that will be executed)
* schedule data (when the powershell code will be executed. this is a cron string, or possibly "asap"/"now")


in a limited configuration such as what we are building, there's really just two jobs to speak of:

1. the initial configuration job that handles node setup and deployment. this job's schedule is "asap" or "now," and fires as soon as it's received.
2. the worker job, which has a defined schedule (every weekday at 7pm, or somesuch), and is the one that kicks off the actual work on a regular basis.

it's not strictly necessary to go any further than this, but this sort of mechanism could be used, for instance, to granularize the checks themselves. say, we set up a schedule to ring checks 1-10 daily at 7pm, but check 11 runs once every two hours, and check 12 runs every half hour. or we could set up a separate daily task to run a backup report.


in what format/method does the service need to send the response data back to the server?

# Jobs vs Checks
Job: something that the client's service runs
Check: something that the client's powershell runs
