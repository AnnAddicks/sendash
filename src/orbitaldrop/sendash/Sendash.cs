using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.InteropServices;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.ServiceProcess;
using System.Text;
using System.IO;
using System.Management.Automation;
using System.Management.Automation.Runspaces;
using NCrontab;

namespace sendash {

    public enum ServiceState {
        SERVICE_STOPPED = 0x00000001,
        SERVICE_START_PENDING = 0x00000002,
        SERVICE_STOP_PENDING = 0x00000003,
        SERVICE_RUNNING = 0x00000004,
        SERVICE_CONTINUE_PENDING = 0x00000005,
        SERVICE_PAUSE_PENDING = 0x00000006,
        SERVICE_PAUSED = 0x00000007,
    }

    [StructLayout(LayoutKind.Sequential)]
    public struct ServiceStatus {
        public long dwServiceType;
        public ServiceState dwCurrentState;
        public long dwControlsAccepted;
        public long dwWin32ExitCode;
        public long dwServiceSpecificExitCode;
        public long dwCheckPoint;
        public long dwWaitHint;
    };

    public partial class Sendash : ServiceBase {
        [DllImport("advapi32.dll", SetLastError = true)]
        private static extern bool SetServiceStatus(IntPtr handle, ref ServiceStatus serviceStatus);
        [DllImport("advapi32.dll", EntryPoint = "QueryServiceStatus", CharSet = CharSet.Auto)]
        internal static extern bool QueryServiceStatus(IntPtr hService, ref ServiceStatus dwServiceStatus);

        //private System.ComponentModel.IContainer components;
        //private System.Diagnostics.EventLog eventLog1;


        public Sendash() {
            InitializeComponent();

            eventLog1 = new System.Diagnostics.EventLog();
            if (!System.Diagnostics.EventLog.SourceExists("Sendash")) {
                System.Diagnostics.EventLog.CreateEventSource("Sendash", "Sendash");
            }
            eventLog1.Source = "Sendash";
            eventLog1.Log = "Sendash";
        }

        protected override void OnStart(string[] args) {
            // Update the service state to Start Pending.
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.dwCurrentState = ServiceState.SERVICE_START_PENDING;
            serviceStatus.dwWaitHint = 100000;
            SetServiceStatus(this.ServiceHandle, ref serviceStatus);

            eventLog1.WriteEntry("In OnStart");

            // Set up a timer to trigger every minute.
            System.Timers.Timer timer = new System.Timers.Timer();
            timer.Interval = 60000; // 60 seconds
            timer.Elapsed += new System.Timers.ElapsedEventHandler(this.OnTimer);
            timer.Start();

            // Update the service state to Running.
            serviceStatus.dwCurrentState = ServiceState.SERVICE_RUNNING;
            SetServiceStatus(this.ServiceHandle, ref serviceStatus);
        }

        protected override void OnStop() {
            eventLog1.WriteEntry("In onStop.");
        }


        public void OnTimer(object sender, System.Timers.ElapsedEventArgs args) {

            eventLog1.WriteEntry("Tick: " + DateTime.Now.ToString(), EventLogEntryType.Information, 501);

            // this will (hopefully) be stored in the registry by another subroutine someplace.
            // we'll need to do some error handling on this in case the Parse() below fails.
            // there's a function called TryParse(), maybe worth looking into
            var CronTabString = "*/10 * * * *"; // every two minutes.

            var Schedule = CrontabSchedule.Parse(CronTabString);
            var NextRun = Schedule.GetNextOccurrence(DateTime.Now);

            if ((NextRun > DateTime.Now) && (NextRun < DateTime.Now.AddMinutes(1))) {
                // WE ARE IN THE ZONE
                // IT IS GO TIME
                // CONQUER, YOU LOUSY BASTARDS

                eventLog1.WriteEntry("Tick matches schedule! (TimeNow: " + DateTime.Now.ToString() + " ; Schedule: " + CronTabString + "; NextRun: " + NextRun.ToString() + ")", EventLogEntryType.Information, 502);

                string PathToInput = Path.GetFullPath(@"c:\sendash\input.txt");



                if (File.Exists(PathToInput)) {

                    eventLog1.WriteEntry("Input File Located (" + PathToInput + ")", EventLogEntryType.Information, 595);





                    string ScriptContents;
                    try {
                        ScriptContents = File.ReadAllText(PathToInput);

                        Runspace runspace = RunspaceFactory.CreateRunspace();
                        runspace.Open();
                        using (PowerShell PowerShellInstance = PowerShell.Create()) {
                            PowerShellInstance.Runspace = runspace;
                            PowerShellInstance.AddScript(ScriptContents);
                            Collection<PSObject> results = PowerShellInstance.Invoke();
                            
                            
                            
                            // Do something with result ... 

                            String OutputString = "\n" + ScriptContents + "\n";

                            // loop through each output object item
                            foreach (PSObject outputItem in results) {
                                // if null object was dumped to the pipeline during the script then a null
                                // object may be present here. check for null to prevent potential NRE.
                                if (outputItem != null) {
                                    //TODO: do something with the output item 
                                    OutputString += outputItem.BaseObject.GetType().FullName;
                                    OutputString += outputItem.BaseObject.ToString() + "\n";
                                }
                            }


                            eventLog1.WriteEntry("Execution Results: " + OutputString, EventLogEntryType.Information, 600);

                        }
                        runspace.Close();

                    } catch (Exception e) {
                        eventLog1.WriteEntry("The file could not be read (" + PathToInput + ")" + e.Message, EventLogEntryType.Error, 596);
                    }
                }
            }
        }
    }
}
