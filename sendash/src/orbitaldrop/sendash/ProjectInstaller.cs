using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration.Install;
using System.Linq;
using System.Diagnostics;
using System.ServiceProcess;


namespace sendash {
    [RunInstaller(true)]
    public partial class ProjectInstaller : System.Configuration.Install.Installer {
        public ProjectInstaller() {
            InitializeComponent();
            
            EventLogInstaller installer = FindInstaller(this.Installers);
            if (installer != null) {
                installer.Log = "Sendash"; // enter your event log name here
            }
        }

        private void serviceInstaller1_AfterInstall(object sender, InstallEventArgs e) {

        }

        private EventLogInstaller FindInstaller(InstallerCollection installers) {
            foreach (Installer installer in installers) {
                if (installer is EventLogInstaller) {
                    return (EventLogInstaller)installer;
                }

                EventLogInstaller eventLogInstaller = FindInstaller(installer.Installers);
                if (eventLogInstaller != null) {
                    return eventLogInstaller;
                }
            }
            return null;
        }

        private void serviceInstallerService1_Committed(object sender, InstallEventArgs e) {
            var serviceInstaller = sender as ServiceInstaller;
            // Start the service after it is installed.
            if (serviceInstaller != null && serviceInstaller.StartType == ServiceStartMode.Automatic) {
                var serviceController = new ServiceController(serviceInstaller.ServiceName);
                serviceController.Start();
            }
        }

    }
}
