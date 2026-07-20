import React from 'react';
import styles from './CohortDetails.module.css';

function CohortDetails({ cohort }) {
  const headingStyle = {
    color: cohort.status === 'ongoing' ? 'green' : 'blue'
  };

  return (
    <div className={styles.box}>
      <h3 style={headingStyle}>{cohort.code}</h3>
      <dl>
        <dt>Name</dt>
        <dd>{cohort.name}</dd>
        <dt>Status</dt>
        <dd>{cohort.status}</dd>
        <dt>Start Date</dt>
        <dd>{cohort.startDate}</dd>
        <dt>Participants</dt>
        <dd>{cohort.participants}</dd>
      </dl>
    </div>
  );
}

export default CohortDetails;
