/*
 * Created by SharpDevelop.
 * User: brian
 * Date: 7/31/2015
 * Time: 6:29 PM
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using System.Collections.Generic;
using System.ServiceProcess;
using System.Text;

namespace Dropship {
	static class Program {
		/// <summary>
		/// This method starts the service.
		/// </summary>
		static void Main() {
			// To run more than one service you have to add them here
			ServiceBase.Run(new ServiceBase[] { new Dropship() });
		}
	}
}
