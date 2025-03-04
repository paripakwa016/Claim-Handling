const API_BASE_URL = "http://localhost:9797/api/claims";

document.addEventListener("DOMContentLoaded", function () {

    // Handle Create Claim Submission
    document.getElementById("createClaimForm").addEventListener("submit", async (e) => {
        e.preventDefault();


        const policyId = document.querySelector("[name='policy.policyId']").value;

        const claimData = {
            policy: { policyId: parseInt(policyId) },
            claimDate: document.querySelector("[name='claimDate']").value,
            claimDescription: document.querySelector("[name='claimDescription']").value,
            claimAmount: parseFloat(document.querySelector("[name='claimAmount']").value),
            claimStatus: document.querySelector("[name='claimStatus']").value
        };

        console.log("Sending request:", claimData); // Debugging Log

        try {
            const response = await fetch(`${API_BASE_URL}`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(claimData),
            });

            console.log("Response status:", response.status);

            if (!response.ok) throw new Error("Failed to save claim");

            const result = await response.json();
            alert("Claim saved successfully! ID: " + result.claimId);
            document.getElementById("createClaimForm").reset();
        } catch (error) {
            console.error("Error:", error);
            alert("Error: " + error.message);
        }
    });

    // Handle Update Claim Submission
  document.getElementById("updateClaimForm").addEventListener("submit", async (e) => {
      e.preventDefault();

      const claimId = document.querySelector("[name='claimIdUpdate']").value;
      const claimDescriptionField = document.querySelector("[name='claimDescriptionUpdate']");
      const claimStatusField = document.querySelector("[name='claimStatusUpdate']");

      if (!claimDescriptionField || !claimStatusField) {
          alert("❌ Error: Some form fields are missing!");
          return;
      }

      const claimDescription = claimDescriptionField.value.trim();
      const claimStatus = claimStatusField.value;

      console.log("🟢 Captured claimDescription:", claimDescription);
      console.log("🟢 Captured claimStatus:", claimStatus);

      if (!claimId) {
          alert("❌ Please enter a valid Claim ID.");
          return;
      }

      const updatedData = {};

      if (claimDescription !== "") {
          updatedData.claimDescription = claimDescription;
      }
      if (claimStatus !== "") {
          updatedData.claimStatus = claimStatus;
      }

      if (Object.keys(updatedData).length === 0) {
          alert("❌ No changes detected. Please update at least one field.");
          return;
      }

      console.log("🟡 Sending PUT Request:", JSON.stringify(updatedData));

      try {
          const response = await fetch(`http://localhost:9797/api/claims/${claimId}`, {
              method: "PUT",
              headers: {
                  "Content-Type": "application/json",
              },
              body: JSON.stringify(updatedData),
          });

          console.log("🟡 Response Status:", response.status);

          if (!response.ok) {
              throw new Error("Failed to update claim");
          }

          alert("✅ Claim updated successfully!");
          document.getElementById("updateClaimForm").reset();
      } catch (error) {
          console.error("❌ Error:", error);
          alert("Error: " + error.message);
      }
  });




    // Handle View Claims
    document.getElementById("viewClaimForm").addEventListener("submit", async (e) => {
        e.preventDefault();

        const policyId = document.querySelector("[name='policyId']").value;

        console.log("Fetching claims for policy:", policyId); // Debugging Log
        console.log(`${API_BASE_URL}/${policyId}`);

        try {
            const response = await fetch(`${API_BASE_URL}/${policyId}`, {
                method: "GET",
            });

            console.log("Response status:", response.status);

            if (!response.ok) {
                throw new Error("Failed to fetch claims");
            }

            const claim = await response.json();


            console.log(claim);
        } catch (error) {
            console.error("Error:", error);
            alert("Error: " + error.message);
        }
    });
});
