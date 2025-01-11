document.addEventListener('DOMContentLoaded', function() {
    const allData = window.artistReports || [];
    const ctx = document.getElementById('artistReportChart').getContext('2d');
    let chartInstance;
    let currentChartData = null;
    const textElement = document.querySelector('#content-header');

    // Load html2pdf library
    const script = document.createElement('script');
    script.src = 'https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js';
    document.head.appendChild(script);

    // Function to get limited data for "all" view
    function getLimitedData(data) {
        return data.slice(0, 15);
    }

    // Function to update chart with color options
    function updateChart({ labels, data }, isPrinting = false) {
        // Store current chart data
        currentChartData = { labels, data };

        if (chartInstance) {
            chartInstance.destroy();
        }

        chartInstance = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Total Listeners',
                    data: data,
                    backgroundColor: [
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 206, 86, 0.2)'
                    ],
                    borderColor: [
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 206, 86, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        labels: {
                            color: isPrinting ? 'black' : 'white'
                        }
                    }
                },
                scales: {
                    x: {
                        ticks: { color: isPrinting ? 'black' : 'white' }
                    },
                    y: {
                        beginAtZero: true,
                        ticks: { color: isPrinting ? 'black' : 'white' }
                    }
                }
            }
        });
    }

    async function preparePrintChart() {
        const selectedArtist = artistDropdown.value;
        
        if (selectedArtist === 'all') {
            const limitedData = getLimitedData(allData);
            updateChart({
                labels: limitedData.map(report => report.artistName),
                data: limitedData.map(report => report.totalListeners)
            }, true);
        } else if (currentChartData) {
            updateChart(currentChartData, true);
        }
    }

    async function generatePDF() {
        const element = document.querySelector('.body-content');
        const tableContainer = document.getElementById('songTableContainer');
        const fileName = `${textElement.textContent.replace(/\s+/g, '_')}.pdf`;

        // Temporarily hide the print button and table
        const printButton = document.getElementById('printButton');
        const header = document.getElementById('content-header');
        const originalPrintButtonDisplay = printButton.style.display;
        printButton.style.display = 'none';
        header.style.color = 'black';

        if (tableContainer) {
            tableContainer.style.display = 'none';
        }

        // Configure PDF options
        const opt = {
            margin: [0.5, 0.5],
            filename: fileName,
            image: { type: 'jpeg', quality: 1 },
            html2canvas: { 
                scale: 2,
                backgroundColor: '#FFFFFF'
            },
            jsPDF: { unit: 'in', format: 'a3', orientation: 'landscape' }
        };

        try {
            // Generate PDF
            await html2pdf().set(opt).from(element).save();
            
            // Refresh the page after a delay
            setTimeout(() => {
                window.location.reload();
            }, 100);
        } catch (error) {
            console.error('PDF generation failed:', error);
            alert('Failed to generate PDF. Please try again.');
            
            // Restore the print button and table visibility on error
            printButton.style.display = originalPrintButtonDisplay;
            if (tableContainer) {
                tableContainer.style.display = 'table';
            }
        }
    }

    const printButton = document.getElementById('printButton');
    if (printButton) {
        printButton.addEventListener('click', async function() {
            await preparePrintChart();
            // Small delay to ensure chart is rendered
            setTimeout(generatePDF, 500);
        });
    }

    // Initial chart rendering
    function renderInitialChart() {
        if (allData.length > 0) {
            const limitedData = getLimitedData(allData);
            updateChart({
                labels: limitedData.map(report => report.artistName),
                data: limitedData.map(report => report.totalListeners)
            });

            if (textElement) {
                textElement.textContent = 'All Artists Report';
            }
        }
    }

    // Dropdown event listener
    const artistDropdown = document.getElementById('artistDropdown');
    if (artistDropdown) {
        artistDropdown.addEventListener('change', async (event) => {
            const selectedOption = artistDropdown.options[artistDropdown.selectedIndex];
            const selectedArtist = selectedOption.value;
            const tableContainer = document.getElementById('songTableContainer');
            let selectedArtistName = selectedOption.textContent;
            selectedArtistName = selectedArtistName.substring(0, selectedArtistName.length - 5);

            if (textElement) {
                textElement.textContent = selectedArtist === 'all' 
                    ? 'All Artists Report' 
                    : `${selectedArtistName} Report`;
            }

            if (selectedArtist === "all") {
                const limitedData = getLimitedData(allData);
                updateChart({
                    labels: limitedData.map(report => report.artistName),
                    data: limitedData.map(report => report.totalListeners)
                });
                if (tableContainer) tableContainer.style.display = "none";
                return;
            }

            try {
                const response = await fetch(`/artist-songs?idArtist=${selectedArtist}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch song data');
                }

                const songs = await response.json();

                // Update chart with song data
                updateChart({
                    labels: songs.map(song => song.title),
                    data: songs.map(song => song.listener)
                });

                // Update song table
                if (tableContainer) {
                    const tableBody = document.getElementById('songTable');
                    if (tableBody) {
                        const tableHTML = songs.map(song => `
                            <tr>
                                <td>${song.title}</td>
                                <td>${song.listener.toLocaleString()}</td>
                                <td><a href="${song.url}" target="_blank">Play</a></td>
                            </tr>
                        `).join('');    
                        
                        tableBody.innerHTML = tableHTML;
                        tableContainer.style.display = "table";
                    }
                }
            } catch (error) {
                console.error('Error fetching song data:', error);
                alert('Failed to load song data. Please try again.');
            }
        });
    }

    // Render initial chart
    renderInitialChart();
});

function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('active');
}