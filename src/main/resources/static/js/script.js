const API_BASE_URL = "http://localhost:9797/api/claims";

document.addEventListener("DOMContentLoaded", function () {

    // Handle Create Claim Submission
    document.getElementById("createClaimForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        console.log("hello world");

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

        const claimId = document.querySelector("[name='claimId']").value;
        const updatedData = {
            claimDescription: document.querySelector("[name='claimDescription']").value,
            claimStatus: document.querySelector("[name='claimStatus']").value,
        };

        console.log("Updating claim:", claimId, updatedData); // Debugging Log

        try {
            const response = await fetch(`${API_BASE_URL}/${claimId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(updatedData),
            });

            console.log("Response status:", response.status);

            if (!response.ok) {
                throw new Error("Failed to update claim");
            }

            alert("Claim updated successfully!");
            document.getElementById("updateClaimForm").reset();
        } catch (error) {
            console.error("Error:", error);
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

            const claims = await response.json();
            let output = claims.map(
                (claim) =>
                    `ID: ${claim.claimId}, Amount: ${claim.claimAmount}, Status: ${claim.claimStatus}`
            ).join("\n");

            alert("Claims:\n" + output);
        } catch (error) {
            console.error("Error:", error);
            alert("Error: " + error.message);
        }
    });
});
